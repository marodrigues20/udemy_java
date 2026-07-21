package uk.co.hashtable;

public class Main {

    static int passed = 0;
    static int failed = 0;

    static void main() {
        test_setAndGet_singleValue();
        test_get_nonExistentKey_returnsNull();
        test_set_updatesExistingKey();
        test_set_multipleKeys_sameBucket_collisionHandled();
        test_keys_returnsAllInsertedKeys();
        test_keys_emptyTable_returnsEmptyList();
        test_remove_existingKey_firstNodeInBucket();
        test_remove_nonExistentKey_returnsFalse();
        test_remove_middleNodeInChain();
        test_remove_lastNodeInChain();
        test_remove_fromEmptyBucket_returnsFalse();
        test_set_updatesLastNodeInChain();

        System.out.println("\n---");
        System.out.println("Passed: " + passed + " | Failed: " + failed);
    }

    static void test_setAndGet_singleValue() {
        HashTable hashTable = new HashTable();
        hashTable.set("Alex", 16);
        check("set_and_get_singleValue", hashTable.get("Alex"), 16);
    }

    static void test_get_nonExistentKey_returnsNull() {
        HashTable hashTable = new HashTable();
        check("get_nonExistentKey_returnsNull", hashTable.get("DoesNotExist"), null);
    }

    static void test_set_updatesExistingKey() {
        HashTable hashTable = new HashTable();
        hashTable.set("Alex", 16);
        hashTable.set("Alex", 45);
        check("set_updatesExistingKey", hashTable.get("Alex"), 45);
    }

    static void test_set_multipleKeys_sameBucket_collisionHandled() {
        HashTable hashTable = new HashTable();
        // keyA, keyH e Alex colidem no mesmo bucket (índice 4) pelo hash() atual
        hashTable.set("keyA", 1);
        hashTable.set("keyH", 2);
        hashTable.set("Alex", 3);

        check("collision_keyA", hashTable.get("keyA"), 1);
        check("collision_keyH", hashTable.get("keyH"), 2);
        check("collision_Alex", hashTable.get("Alex"), 3);
    }

    static void test_keys_returnsAllInsertedKeys() {
        HashTable hashTable = new HashTable();
        hashTable.set("Alex", 16);
        hashTable.set("Mario", 30);
        hashTable.set("Joana", 25);

        var keys = hashTable.keys();
        boolean result = keys.size() == 3
                && keys.contains("Alex")
                && keys.contains("Mario")
                && keys.contains("Joana");

        check("keys_returnsAllInsertedKeys", result, true);
    }

    static void test_keys_emptyTable_returnsEmptyList() {
        HashTable hashTable = new HashTable();
        check("keys_emptyTable_returnsEmptyList", hashTable.keys().isEmpty(), true);
    }

    static void test_remove_existingKey_firstNodeInBucket() {
        HashTable hashTable = new HashTable();
        hashTable.set("Alex", 16);
        boolean removed = hashTable.remove("Alex");
        check("remove_existingKey_returnsTrue", removed, true);
        check("remove_existingKey_getReturnsNullAfter", hashTable.get("Alex"), null);
    }

    static void test_remove_nonExistentKey_returnsFalse() {
        HashTable hashTable = new HashTable();
        check("remove_nonExistentKey_returnsFalse", hashTable.remove("Ghost"), false);
    }

    static void test_remove_middleNodeInChain() {
        HashTable hashTable = new HashTable();
        // keyA -> keyH -> Alex, todos colidem no bucket 4
        hashTable.set("keyA", 1);
        hashTable.set("keyH", 2);
        hashTable.set("Alex", 3);

        boolean removed = hashTable.remove("keyH"); // remove o do meio da cadeia

        check("remove_middleNode_returnsTrue", removed, true);
        check("remove_middleNode_keyHGone", hashTable.get("keyH"), null);
        check("remove_middleNode_keyAIntact", hashTable.get("keyA"), 1);
        check("remove_middleNode_AlexIntact", hashTable.get("Alex"), 3);
    }

    static void test_remove_lastNodeInChain() {
        HashTable hashTable = new HashTable();
        // keyA e keyH colidem (bucket 4); keyH é o último node da cadeia
        hashTable.set("keyA", 1);
        hashTable.set("keyH", 2);

        boolean removed = hashTable.remove("keyH"); // remove o último da cadeia

        check("remove_lastNode_returnsTrue", removed, true);
        check("remove_lastNode_keyHGone", hashTable.get("keyH"), null);
        check("remove_lastNode_keyAIntact", hashTable.get("keyA"), 1);
    }

    static void test_remove_fromEmptyBucket_returnsFalse() {
        HashTable hashTable = new HashTable();
        check("remove_fromEmptyBucket_returnsFalse", hashTable.remove("Anything"), false);
    }

    static void test_set_updatesLastNodeInChain() {
        HashTable hashTable = new HashTable();
        // keyA, keyH, Alex colidem no bucket 4 (keyA é o head, Alex é o último da cadeia)
        hashTable.set("keyA", 1);
        hashTable.set("keyH", 2);
        hashTable.set("Alex", 3);

        hashTable.set("Alex", 99); // tenta ATUALIZAR o último node da cadeia

        check("set_updatesLastNodeInChain", hashTable.get("Alex"), 99);
    }

    // ---------- helper ----------

    static void check(String testName, Object actual, Object expected) {
        boolean ok = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        if (ok) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName + " -> expected: " + expected + ", actual: " + actual);
        }
    }
}