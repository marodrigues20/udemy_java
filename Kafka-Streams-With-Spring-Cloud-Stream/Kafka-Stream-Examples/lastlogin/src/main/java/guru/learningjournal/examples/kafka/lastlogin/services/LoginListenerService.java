package guru.learningjournal.examples.kafka.lastlogin.services;

import guru.learningjournal.examples.kafka.lastlogin.bindings.UserListenerBinding;
import guru.learningjournal.examples.kafka.lastlogin.model.UserDetails;
import guru.learningjournal.examples.kafka.lastlogin.model.UserLogin;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Log4j2
@Service
@EnableBinding(UserListenerBinding.class)
public class LoginListenerService {

    /**
     * We start with left KTable, apply the join on the right KTable.
     * - Te logins KTable is my left KTable, and the users are the right KTable.
     * - The next arguemnt is the value joiner lambda.
     * - The first lambda argument is the left record
     * - The second lambda argument is the right record.
     * - Lambda Body implements the join operation.
     * - The join operatino is simple. I am updating the user's last login time with the current login time.
     * - The result of the join is a KTable, so I am converting it to a KStream and printing the results.
     * - While joining two topics, you must ensure that you are not violating the join prerequisite.
     * - The data in my user's topic is keyed by the login id. On the other side, data in the user login topic is also
     *   keyed by login id. Having the same key for both the topics will ensure that all the data from both the topics
     *   for a specific user will flow to the stream thread.
     * - While working with Kafka, knowing your Topic and the partitioning strategy is critical. You must ensure that
     * the data is co-partitioned for the join operation to happen. We cannot perform a join if the data for the same
     * key flows to different threads. And we are fulfilling that condition in the current example.
     */
    @StreamListener
    public void process(@Input("user-master-channel") KTable<String, UserDetails> users,
                        @Input("user-login-channel") KTable<String, UserLogin> logins) {

        users.toStream().foreach((k, v) -> log.info("User Key: {}, Last Login: {}, Value{}",
                k, Instant.ofEpochMilli(v.getLastLogin()).atOffset(ZoneOffset.UTC), v));

        logins.toStream().foreach((k, v) -> log.info("Login Key: {}, Last Login: {}, Value{}",
                k, Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC), v));

        logins.join(users, (l, u) -> {
            u.setLastLogin(l.getCreatedTime());
            return u;
        }).toStream().foreach((k, v) -> log.info("Updated Last Login Key: {}, Last Login: {}", k,
                Instant.ofEpochMilli(v.getLastLogin()).atOffset(ZoneOffset.UTC)));

    }

}
