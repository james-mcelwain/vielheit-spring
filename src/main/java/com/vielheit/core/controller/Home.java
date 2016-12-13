package com.vielheit.core.controller;


import com.vielheit.core.repository.UserRepository;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLObjectType.newObject;

@Component
@Path("/")
public class Home {
    @Inject
    UserRepository userRepository;

    GraphQLObjectType user = newObject()
            .name("User")
            .field(field -> field.type(GraphQLString)
                    .name("firstName")
                    .dataFetcher(new DataFetcher() {
                        @Override
                        public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
                            return userRepository.findOne(1L).getFirstName();
                        }
                    }))
            .build();

    GraphQLSchema schema = GraphQLSchema.newSchema()
            .query(user)
            .build();

    @GET
    public String homeCtrl() {
        ExecutionResult result = new GraphQL(schema).execute("{}");
        return result.getData().toString();
    }
}
