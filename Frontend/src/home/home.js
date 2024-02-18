import React from 'react';

import BackgroundImg from '../commons/images/future-medicine.jpg';

import {Button, Col, Container, FormGroup, Input, Jumbotron, Label, Row} from 'reactstrap';
import validate from "../person/components/validators/person-validators";
import * as API_USERS from "../user/api/user-api";
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};
const textStyle = {
    color: 'white',
    position: 'center'
};

class Home extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                username: {
                    value: '',
                    placeholder: 'Username',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
                password: {
                    value: '',
                    placeholder: 'Password',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3
                    }
                },
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    checkUser(user) {
        return API_USERS.postUser(user, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Identified person with role: " + result);
                if(result === 1) {
                    window.location.href = '/administrator';
                } else {
                    window.location.href = '/client';
                }
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    handleSubmit() {
        let user = {
            username: this.state.formControls.username.value,
            password: this.state.formControls.password.value
        };

        console.log(user);
        this.checkUser(user);
    }

    render() {

        return (

                <div>
                   <Jumbotron fluid style={backgroundStyle}>
                        <Container fluid>
                           <h1 className="display-3" style={textStyle}> <b> Login </b> </h1>
                        </Container>

                    <FormGroup id='username'>
                        <Label for='usernameField' style={textStyle}> Username: </Label>
                        <Input name='username' id='usernameField' placeholder={this.state.formControls.username.placeholder}
                            onChange={this.handleChange}
                            defaultValue={this.state.formControls.username.value}
                            touched={this.state.formControls.username.touched? 1 : 0}
                            valid={this.state.formControls.username.valid}
                            required
                        />
                        {this.state.formControls.username.touched && !this.state.formControls.username.valid &&
                        <div className={"error-message row"}> * Username must have at least 3 characters </div>}
                    </FormGroup>

                    <FormGroup id='password'>
                        <Label for='passwordField' style={textStyle}> Password: </Label>
                        <Input name='password' id='passwordField' placeholder={this.state.formControls.password.placeholder}
                            onChange={this.handleChange}
                            defaultValue={this.state.formControls.password.value}
                            touched={this.state.formControls.password.touched? 1 : 0}
                            valid={this.state.formControls.password.valid}
                            required
                        />
                        {this.state.formControls.password.touched && !this.state.formControls.password.valid &&
                        <div className={"error-message"}> * Password must have at least 3 characters</div>}
                    </FormGroup>

                    <Row>
                        <Col sm={{size: '4', offset: 8}}>
                            <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>  Submit </Button>
                        </Col>
                    </Row>

                    {
                        this.state.errorStatus > 0 &&
                        <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                    }

                   </Jumbotron>

                </div>
        )
    };
}

export default Home
