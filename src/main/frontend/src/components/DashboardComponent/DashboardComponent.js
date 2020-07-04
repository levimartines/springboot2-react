import React, {Component} from "react";
import {Link} from "react-router-dom";
import './DashboardComponent.css';
import DashboardService from "../../services/DashboardService";

class DashboardComponent extends Component {
  constructor(props) {
    super(props);
    this.retrieveWelcomeMessage = this.retrieveWelcomeMessage.bind(this);
    this.state = {
      welcomeMessage: '',
    }
  }

  render() {
    return (
      <>
        <div>
          <h4>Dashboard - Welcome {this.props.match.params.name} </h4>
          <br/><br/>
          You can manage your todos&nbsp;<Link to={"/todos"}>here</Link> !
          <br/><br/>
          <button onClick={this.retrieveWelcomeMessage}>Get Welcome Message
          </button>
        </div>

        <div>
          <br/>
          {this.state.welcomeMessage}
        </div>
      </>
    );
  }

  retrieveWelcomeMessage() {
    DashboardService.getDashboardData()
    .then(res => this.handleSuccessfulResponse(res))
    .catch(err => this.handleError(err));
  }

  handleSuccessfulResponse(response) {
    this.setState({welcomeMessage: response.data})
  }

  handleError(error) {
    let errorMessage = '';
    if (error.message) {
      errorMessage += error.message;
    }
    if (error.response && error.response.data) {
      errorMessage += error.response.data.message
    }
    this.setState({welcomeMessage: errorMessage})
  }
}

export default DashboardComponent;
