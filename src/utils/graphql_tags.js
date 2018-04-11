import gql from 'graphql-tag';

export const LOG_INTERACTION = gql`
  mutation LogUserInteractionMutation($type: String!, $movieId: String!) {
    logUserInteraction(type: $type, movieId: $movieId) {
      interactionType
    }
  }
`;

export const SIGNUP_MUTATION = gql`
  mutation CreateUserMutation($authProvider: AuthData!, $name: String!) {
    createUser(authProvider: $authProvider, name: $name) {
      id
    }
  }
`;

export const SIGNIN_MUTATION = gql`
  mutation SigninUserMutation($auth: AuthData) {
    signinUser(auth: $auth) {
      token
    }
  }
`;
