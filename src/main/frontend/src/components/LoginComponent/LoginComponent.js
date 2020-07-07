import React, {Component} from "react";
import AuthenticationService from "../../services/AuthenticationService";
import "./LoginComponent.css"
import {Link} from "react-router-dom";

class LoginComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      hasLoginFailed: false,
      showSuccessMessage: false,
    }

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

  }

  render() {
    return (
      <div className="login-form">
        <br/>
        <form onSubmit={this.handleSubmit}>
          <label htmlFor="username">Username: </label>
          <input
            type="text"
            name="username"
            value={this.state.username}
            placeholder="user@email.com"
            onChange={this.handleChange}
          />

          <label htmlFor="password">Password: </label>
          <input
            type="password"
            name="password"
            value={this.state.password}
            onChange={this.handleChange}
          />
          <br/>
          <button className="btn">Login</button>
        </form>
        <div className="errorContainer">
          {
            this.state.hasLoginFailed && <p><strong>Invalid username or
              password</strong></p>
          }
        </div>
        <br/><br/>
        <div className="register">
          <div>
            Register a new Account <Link to={"/signup"}>here</Link> !
          </div>
        </div>

      </div>
    )
  }

  handleSubmit(event) {
    event.preventDefault();
    const {username, password} = this.state;
    AuthenticationService.tryLogin(username, password).then(res => {
      AuthenticationService.registerSuccessfulLogin(username, res.data);
      this.props.history.push(`/dashboard/${this.state.username}`);
    }).catch(err => {
      this.setState({hasLoginFailed: true});
    })
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value
    })
  }

}

export default LoginComponent;
