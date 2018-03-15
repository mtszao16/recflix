import gql from "graphql-tag";

export const LOG_INTERACTION = gql`
  mutation LogUserInteractionMutation($time: String!, $type: String!) {
    logUserInteraction(time: $time, type: $type) {
      interactionType
    }
  }
`;
