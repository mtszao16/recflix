import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { graphql, compose } from 'react-apollo';
import PropTypes from 'prop-types';
import { GET_ALL_MOVIES } from '../utils/graphql_tags';

class Landing extends Component {
  static propTypes = {
    getAllMovies: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allMovies: PropTypes.array
    }).isRequired
  };

  render() {
    const {
      getAllMovies: { allMovies }
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
                  onClick={() => {
                    this.props.history.push(`/movie/${movie.id}`);
                  }}
                >
                  Card title
                </h5>
              </div>
            </div>
          ))}
      </div>
    );
  }
}

export default compose(graphql(GET_ALL_MOVIES, { name: 'getAllMovies' }))(
  withRouter(Landing)
);
