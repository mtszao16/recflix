import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { withRouter } from 'react-router';
import { AUTH_TOKEN } from '../utils/constants';

class Header extends Component {
  render() {
    const authToken = localStorage.getItem(AUTH_TOKEN);
    return (
      <nav className="navbar transparent-header">
        <div className="navbar-header">
          <Link className="navbar-brand" to="/">
            <img alt="Logo" src="./assets/images/recflixlogo.png" id="logo" />
          </Link>
        </div>
        <div className="flex flex-fixed">
          {authToken ? (
            <div className="navbar">
              <Link
                to="/login"
                className="btn btn-primary"
                onClick={() => {
                  localStorage.removeItem(AUTH_TOKEN);
                }}
              >
                Log Out
              </Link>
            </div>
          ) : (
            <div className="navbar">
              <Link to="/login" className="btn btn-primary">
                Log In
              </Link>
            </div>
          )}
        </div>
      </nav>
    );
  }
}

export default withRouter(Header);
