import React, {Component} from "react";
import "./RegisterComponent.css"
import SignupService from "../../services/SignupService";
import AuthenticationService from "../../services/AuthenticationService";
import {Link} from "react-router-dom";

class RegisterComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
    }

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  render() {
    return (
      <div className="register-form">
        <br/>
        <form onSubmit={this.handleSubmit}>
          <div className="form-row">
            <div className="col-6">
              <label htmlFor="username">Username: </label>
              <input
                type="text"
                name="username"
                value={this.state.username}
                placeholder="Login"
                onChange={this.handleChange}
              />
              <label htmlFor="email">Email: </label>
              <input
                type="email"
                name="email"
                value={this.state.email}
                placeholder="user@email.com"
                onChange={this.handleChange}
              />
            </div>

            <div className="col-6">
              <label htmlFor="password">Password: </label>
              <input
                type="password"
                name="password"
                value={this.state.password}
                onChange={this.handleChange}
              />
              <label htmlFor="confirmPassword">Confirm your password: </label>
              <input
                type="confirmPassword"
                name="confirmPassword"
                value={this.state.confirmPassword}
                onChange={this.handleChange}
              />
            </div>
          </div>

          <br/>
          <button className="btn">SignUp</button>
        </form>

        <div className="errorContainer">
          {
            this.state.hasLoginFailed &&
            <p><strong>Invalid username or
              password</strong></p>
          }
        </div>
        <br/><br/>
        <div className="register">
          <div>
            Back to <Link to={"/"}>Login</Link>
          </div>
        </div>

      </div>
    )
  }

  handleSubmit(event) {
    event.preventDefault();
    let login = this.state.username;
    let {email, password, confirmPassword} = this.state;
    let user = {
      login: login,
      email: email,
      password: password,
      confirmPassword: confirmPassword
    }
    SignupService.registerUser(user).then(res => {
      AuthenticationService.tryLogin(login, password).then(res => {
        AuthenticationService.registerSuccessfulLogin(login, res.data);
        this.props.history.push(`/dashboard/${login}`);
      }).catch(err => {
        this.setState({hasLoginFailed: true});
        console.log(err);
      })
    }).catch(err => {
      this.setState({hasLoginFailed: true});
      console.log(err);
    })
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value
    })
  }

}

export default RegisterComponent;
