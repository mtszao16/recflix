import React, { Component } from 'react';
import { graphql, compose } from 'react-apollo';
import { AUTH_TOKEN } from '../utils/constants';
import { SIGNIN_MUTATION, SIGNUP_MUTATION } from '../utils/graphql_tags';

class Login extends Component {
  state = {
    login: true, // switch between Login and SignUp
    email: '',
    password: '',
    name: ''
  };

  render() {
    return (
      <div>
        <h4 className="mv3">{this.state.login ? 'Login' : 'Sign Up'}</h4>
        <div className="flex flex-column">
          {!this.state.login && (
            <input
              value={this.state.name}
              onChange={e => this.setState({ name: e.target.value })}
              type="text"
              placeholder="Your name"
            />
          )}
          <input
            value={this.state.email}
            onChange={e => this.setState({ email: e.target.value })}
            type="text"
            placeholder="Your email address"
          />
          <input
            value={this.state.password}
            onChange={e => this.setState({ password: e.target.value })}
            type="password"
            placeholder="Choose a safe password"
          />
        </div>
        <div className="flex mt3">
          <div className="pointer mr2 button" onClick={() => this._confirm()}>
            {this.state.login ? 'login' : 'create account'}
          </div>
          <div
            className="pointer button"
            onClick={() => this.setState({ login: !this.state.login })}
          >
            {this.state.login
              ? 'need to create an account?'
              : 'already have an account?'}
          </div>
        </div>
      </div>
    );
  }

  _confirm = async () => {
    const { name, email, password } = this.state;
    if (this.state.login) {
      const result = await this.props.loginMutation({
        variables: {
          auth: {
            email,
            password
          }
        }
      });
      const { token } = result.data.signinUser;
      this._saveUserData(token);
    } else {
      const result = await this.props.signupMutation({
        variables: {
          name,
          authProvider: {
            email,
            password
          }
        }
      });
      const { token } = result.data.createUser;
      this._saveUserData(token);
    }
    this.props.history.push(`/`);
  };

  _saveUserData = token => {
    localStorage.setItem(AUTH_TOKEN, token);
  };
}

export default compose(
  graphql(SIGNUP_MUTATION, { name: 'signupMutation' }),
  graphql(SIGNIN_MUTATION, { name: 'loginMutation' })
)(Login);
