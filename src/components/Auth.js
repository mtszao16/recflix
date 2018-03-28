import React, { Component } from 'react';
import { graphql, compose } from 'react-apollo';
import { AUTH_TOKEN } from '../utils/constants';
import { SIGNIN_MUTATION, SIGNUP_MUTATION } from '../utils/graphql_tags';

class Auth extends Component {
  state = {
    login: true, // switch between Login and SignUp
    email: '',
    password: '',
    name: ''
  };

  handleOnClick = event => {
    event.preventDefault();
    this._confirm();
  };

  render() {
    return (
      <div className="row">
        <div className="col">
          <div className="form">
            <ul className="nav nav-pills nav-fill" role="tablist">
              <li className="nav-item">
                <a
                  className="nav-link active"
                  href="#login"
                  role="tab"
                  data-toggle="tab"
                  onClick={() => {
                    this.setState(prevState => {
                      if (!prevState.login) {
                        return { login: true };
                      }
                    });
                  }}
                >
                  Log In
                </a>
              </li>
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="#signup"
                  role="tab"
                  data-toggle="tab"
                  onClick={() => {
                    this.setState(prevState => {
                      if (prevState.login) {
                        return { login: false };
                      }
                    });
                  }}
                >
                  Sign Up
                </a>
              </li>
            </ul>

            <div className="tab-content">
              <div role="tabpanel" className="tab-pane active" id="login">
                <form className="needs-validation" noValidate>
                  <div className="form-row">
                    <div className="col">
                      <label htmlFor="validationCustomEmail">
                        Email Address
                      </label>
                      <input
                        value={this.state.email}
                        onChange={e => this.setState({ email: e.target.value })}
                        type="email"
                        className="form-control"
                        id="validationCustomEmail"
                        placeholder="Email Address"
                        aria-describedby="inputGroupPrepend"
                        required
                      />
                      <div className="invalid-feedback">
                        Please provide a valid email address.
                      </div>
                    </div>
                  </div>
                  <div className="form-row">
                    <div className="col">
                      <label htmlFor="validationCustomPassword">Password</label>
                      <input
                        value={this.state.password}
                        onChange={e =>
                          this.setState({ password: e.target.value })
                        }
                        type="password"
                        className="form-control"
                        id="validationCustomPassword"
                        placeholder="Password"
                        aria-describedby="inputGroupPrepend"
                        required
                      />
                      <div className="invalid-feedback">
                        Please provide a password.
                      </div>
                    </div>
                  </div>
                  <button
                    className="btn btn-primary btn-block"
                    type="submit"
                    onClick={event => this.handleOnClick(event)}
                  >
                    Log In
                  </button>
                </form>
              </div>

              <div role="tabpanel" className="tab-pane" id="signup">
                <form className="needs-validation" noValidate>
                  <div className="form-row">
                    <div className="col-sm">
                      <label htmlFor="validationCustom01">Name</label>
                      <input
                        value={this.state.name}
                        onChange={e => this.setState({ name: e.target.value })}
                        type="text"
                        className="form-control"
                        id="validationCustom01"
                        placeholder="Name"
                        required
                      />
                      <div className="invalid-feedback">
                        Please provide your name.
                      </div>
                    </div>
                  </div>
                  <div className="form-row">
                    <div className="col">
                      <label htmlFor="validationCustomEmail">
                        Email Address
                      </label>
                      <input
                        value={this.state.email}
                        onChange={e => this.setState({ email: e.target.value })}
                        type="email"
                        className="form-control"
                        id="validationCustomEmail"
                        placeholder="Email Address"
                        aria-describedby="inputGroupPrepend"
                        required
                      />
                      <div className="invalid-feedback">
                        Please provide an email address.
                      </div>
                    </div>
                  </div>
                  <div className="form-row">
                    <div className="col">
                      <label htmlFor="validationCustomPassword">Password</label>
                      <input
                        value={this.state.password}
                        onChange={e =>
                          this.setState({ password: e.target.value })
                        }
                        type="password"
                        className="form-control"
                        id="validationCustomPassword"
                        placeholder="Password"
                        aria-describedby="inputGroupPrepend"
                        required
                      />
                      <div className="invalid-feedback">
                        Please choose a password.
                      </div>
                    </div>
                  </div>
                  <div className="form-group">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        value=""
                        id="invalidCheck"
                        required
                      />
                      <label
                        className="form-check-label"
                        htmlFor="invalidCheck"
                      >
                        Agree to terms and conditions
                      </label>
                      <div className="invalid-feedback">
                        You must agree before signing up.
                      </div>
                    </div>
                  </div>
                  <button
                    className="btn btn-primary btn-block"
                    type="submit"
                    onClick={event => this.handleOnClick(event)}
                  >
                    Sign Up
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div className="col-md">
          <h1> Almost there!</h1>
          <p>
            Sign up and join others in the movie recommendation network and
            receive movie recommendations that are right for you.
          </p>
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
)(Auth);
