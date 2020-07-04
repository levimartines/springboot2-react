import React, {Component} from "react";
import {withRouter} from "react-router";
import {Link} from "react-router-dom";
import AuthenticationService from "../../services/AuthenticationService";
import './HeaderComponent.css'
import logo from '../../assets/logo.png';

class HeaderComponent extends Component {
  render() {
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    return (
      <header className="header">
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
          <img src={logo} alt="Logo"/>
          {isUserLoggedIn &&
          <ul className="navbar-nav">
            <li><Link to="/dashboard/admin"
                      className="nav-link">Dashboard</Link></li>
            <li><Link to="/todos" className="nav-link">Todos</Link></li>
          </ul>}
          <ul className="navbar-nav navbar-collapse justify-content-end">
            {renderSessionControl(isUserLoggedIn)}
          </ul>
        </nav>
      </header>
    )
  }
}

function renderSessionControl(isUserLoggedIn) {
  if (!isUserLoggedIn) {
    return (
      <li className="nav-link">
        <Link to="/" className="nav-link">Login</Link>
      </li>
    );
  } else {
    return (
      <li className="nav-link">
        <Link to="/" onClick={AuthenticationService.logout}
              className="nav-link">Logout
        </Link>
      </li>
    );
  }

}

export default withRouter(HeaderComponent);
