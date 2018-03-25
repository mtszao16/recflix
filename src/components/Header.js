import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { withRouter } from 'react-router';
import { AUTH_TOKEN } from '../utils/constants';

class Header extends Component {
  render() {
    const authToken = localStorage.getItem(AUTH_TOKEN);
    return (
      <nav className="navbar navbar-default">
        <div className="navbar-header">
          <Link className="navbar-brand" to="/">
            Recflix
          </Link>
        </div>
        {authToken && (
          <div className="flex">
            <div className="ml1">|</div>
            <Link to="/" className="ml1 no-underline black">
              submit
            </Link>
          </div>
        )}
        <div className="flex flex-fixed">
          {authToken ? (
            <div
              className="ml1 pointer black"
              onClick={() => {
                localStorage.removeItem(AUTH_TOKEN);
                this.props.history.push(`/`);
              }}
            >
              logout
            </div>
          ) : (
            <Link to="/login" className="ml1 no-underline black">
              login
            </Link>
          )}
        </div>
      </nav>
    );
  }
}

export default withRouter(Header);
