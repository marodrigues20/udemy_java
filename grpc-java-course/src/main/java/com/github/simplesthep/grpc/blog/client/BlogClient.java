package com.github.simplesthep.grpc.blog.client;

import com.proto.blog.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BlogClient {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC Client for Blog");
        BlogClient main = new BlogClient();
        main.run();
    }


    private void run() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        BlogServiceGrpc.BlogServiceBlockingStub blogClient = BlogServiceGrpc.newBlockingStub(channel);

        Blog blog = Blog.newBuilder()
                .setAuthorId("Alex")
                .setTitle("New Blog")
                .setContent("Hello World this is my first blog.")
                .build();

        CreateBlogResponse createResponse = blogClient.createBlog(CreateBlogRequest.newBuilder().setBlog(blog).build());

        System.out.println("Receive create blog response");
        System.out.println(createResponse.toString());

        String blogId = createResponse.getBlog().getId();

        System.out.println("Reading blog...");

        ReadBlogResponse readBlogResponse = blogClient.readBlog(ReadBlogRequest.newBuilder()
                                                            .setBlogId(blogId)
                                                            .build());

        System.out.println(readBlogResponse.toString());

        System.out.println("Reading blog with non existing id...");

        // trigger a not found error 
        /*ReadBlogResponse readBlogResponseNotFound = blogClient.readBlog(ReadBlogRequest.newBuilder()
                                                                               .setBlogId("60f5e93cbeff50443e57d8dd")
                                                                               .build());*/


        Blog newBlog = Blog.newBuilder()
                        .setId(blogId)
                        .setAuthorId("Changed Author")
                        .setTitle("New blog (update)!")
                        .setContent("Hello World this is my first blog! I've added some more content")
                        .build();

        System.out.println("Updating blog...");
        UpdateBlogResponse updateBlogResponse = blogClient.updateBlog(
                UpdateBlogRequest.newBuilder().setBlog(newBlog).build());

        System.out.println("Updated blog...");
        System.out.println(updateBlogResponse.toString());

        System.out.println("Deleting blog...");
        DeleteBlogResponse deleteBlogResponse = blogClient.deleteblog(DeleteBlogRequest.newBuilder().setBlogId(blogId).build());

        System.out.println("Deleted blog...");

        System.out.println("Reading blog...");

        // this one should return NOT_FOUND
        /*ReadBlogResponse readBlogResponseAfterDeletion = blogClient.readBlog(ReadBlogRequest.newBuilder()
                                                                               .setBlogId(blogId)
                                                                               .build());*/

        blogClient.listBlog(ListBlogRequest.newBuilder().build()).forEachRemaining(
                listBlogResponse -> System.out.println(
                        listBlogResponse.getBlog().toString()
                )
        );


    }
}
