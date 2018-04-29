import React, { Component } from 'react';
import { Player, ControlBar, PlaybackRateMenuButton } from 'video-react';
import { withRouter } from 'react-router';
import { graphql, compose } from 'react-apollo';
import PropTypes from 'prop-types';

import CustomForwardReplayControl from './CustomForwardReplayControl';
import CustomPlayToggle from './CustomPlayToggle';
import CustomProgressControl from './CustomProgressControl';

import {
  GET_FILTERED_MOVIES,
  RECORD_FEEDBACK_MUTATION,
  GET_FILTERED_FEEDBACKS
} from '../utils/graphql_tags';
import { getUserId } from '../utils/jwtutils';

const CustomForwardControl = CustomForwardReplayControl('forward');
const CustomReplayControl = CustomForwardReplayControl('replay');

class Display extends Component {
  static propTypes = {
    getMovie: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allMovies: PropTypes.array
    }).isRequired,
    getFeedback: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allFeedbacks: PropTypes.array
    }).isRequired
  };

  constructor(props) {
    super(props);
    this.state = {
      selectedRating: null
    };
  }

  static getDerivedStateFromProps(nextProps, prevState) {
    if (
      nextProps.getFeedback &&
      nextProps.getFeedback.allFeedbacks &&
      nextProps.getFeedback.allFeedbacks.length > 0 &&
      nextProps.getFeedback.allFeedbacks[0].rating === prevState.selectedRating
    ) {
      return null;
    }

    return {
      selectedRating:
        nextProps.getFeedback &&
        nextProps.getFeedback.allFeedbacks &&
        nextProps.getFeedback.allFeedbacks.length > 0 &&
        nextProps.getFeedback.allFeedbacks[0].rating
    };
  }

  onRatingChange = async event => {
    this.setState({ selectedRating: event.target.value });
    await this.props.recordFeedback({
      variables: {
        rating: event.target.value,
        movieId: this.props.match.params.movieId
      }
    });
  };

  render() {
    const {
      getMovie: { allMovies }
    } = this.props;
    if (this.props.getFeedback && this.props.getFeedback.loading) {
      return <div>s</div>;
    }
    return (
      <div className="container">
        <h1 className="text-center">
          {allMovies && allMovies.length > 0 && allMovies[0].name}
        </h1>
        <div className="video-player">
          <div className="col-sm-12">
            <Player
              disableDefaultControls
              src={allMovies && allMovies.length > 0 && allMovies[0].url}
            >
              <ControlBar autoHide>
                <CustomReplayControl seconds={5} order={2.1} />
                <CustomForwardControl seconds={5} order={3.1} />
                <CustomProgressControl order={6} />
                <PlaybackRateMenuButton
                  rates={[5, 3, 1.5, 1, 0.5, 0.1]}
                  order={7.1}
                />
                <CustomPlayToggle />
              </ControlBar>
            </Player>
          </div>
        </div>
        <form>
          <div className="star-rating">
            <input
              type="radio"
              id="5-stars"
              name="rating"
              value="5"
              checked={this.state.selectedRating == 5}
              onChange={this.onRatingChange}
            />
            <label htmlFor="5-stars" className="star">
              &#9733;
            </label>
            <input
              type="radio"
              id="4-stars"
              name="rating"
              value="4"
              checked={this.state.selectedRating == 4}
              onChange={this.onRatingChange}
            />
            <label htmlFor="4-stars" className="star">
              &#9733;
            </label>
            <input
              type="radio"
              id="3-stars"
              name="rating"
              value="3"
              checked={this.state.selectedRating == 3}
              onChange={this.onRatingChange}
            />
            <label htmlFor="3-stars" className="star">
              &#9733;
            </label>
            <input
              type="radio"
              id="2-stars"
              name="rating"
              value="2"
              checked={this.state.selectedRating == 2}
              onChange={this.onRatingChange}
            />
            <label htmlFor="2-stars" className="star">
              &#9733;
            </label>
            <input
              type="radio"
              id="1-star"
              name="rating"
              value="1"
              checked={this.state.selectedRating == 1}
              onChange={this.onRatingChange}
            />
            <label htmlFor="1-star" className="star">
              &#9733;
            </label>
          </div>
        </form>
      </div>
    );
  }
}

export default compose(
  graphql(GET_FILTERED_MOVIES, {
    name: 'getMovie',
    options: props => ({
      variables: {
        filter: {
          id: props.match.params.movieId
        }
      }
    })
  }),
  graphql(GET_FILTERED_FEEDBACKS, {
    name: 'getFeedback',
    options: props => ({
      variables: {
        filter: {
          movieId: props.match.params.movieId,
          userId: getUserId()
        }
      },
      fetchPolicy: 'cache-and-network'
    })
  }),
  graphql(RECORD_FEEDBACK_MUTATION, {
    name: 'recordFeedback'
  })
)(withRouter(Display));
