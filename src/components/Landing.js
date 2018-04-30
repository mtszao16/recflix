import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { graphql, compose } from 'react-apollo';
import PropTypes from 'prop-types';

import {
  GET_ALL_MOVIES,
  ADD_WATCHED_MOVIE_MUTATION,
  GET_ALL_RECOMMENDED_MOVIES
} from '../utils/graphql_tags';
import { getUserId } from '../utils/jwtutils';

class Landing extends Component {
  static propTypes = {
    getAllMovies: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allMovies: PropTypes.array
    }).isRequired,
    getAllMoviesRecommendation: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allMoviesRecommendation: PropTypes.array
    }).isRequired
  };

  handleOnClick = async movie => {
    await this.props.addWatchedMovie({
      variables: {
        movieId: movie.id,
        userId: getUserId()
      }
    });
    this.props.history.push(`/movie/${movie.id}`);
  };

  render() {
    const {
      getAllMovies: { allMovies },
      getAllMoviesRecommendation: { allMoviesRecommendation }
    } = this.props;
    return (
      <div>
        {allMovies &&
          allMovies.map(movie => (
            <div key={movie.id} className="card" style={{ width: '18rem' }}>
              <img className="card-img-top" src="." alt="Card image cap" />
              <div className="card-body">
                <h5
                  className="card-title"
                  onClick={() => this.handleOnClick(movie)}
                >
                  {movie.name}
                </h5>
              </div>
            </div>
          ))}

        <br />
        <br />
        <br />
        <br />
        {allMoviesRecommendation &&
          allMoviesRecommendation.map(movie => (
            <div key={movie.id} className="card" style={{ width: '18rem' }}>
              <img className="card-img-top" src="." alt="Card image cap" />
              <div className="card-body">
                <h5
                  className="card-title"
                  onClick={() => this.handleOnClick(movie)}
                >
                  {movie.name}
                </h5>
              </div>
            </div>
          ))}
      </div>
    );
  }
}

export default compose(
  graphql(GET_ALL_MOVIES, { name: 'getAllMovies' }),
  graphql(GET_ALL_RECOMMENDED_MOVIES, { name: 'getAllMoviesRecommendation' }),
  graphql(ADD_WATCHED_MOVIE_MUTATION, { name: 'addWatchedMovie' })
)(withRouter(Landing));
