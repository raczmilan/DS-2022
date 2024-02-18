import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Table,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
import PersonInsertForm from "./components/personInsert-form";
import PersonDeleteForm from "./components/personDelete-form";
import PersonUpdateForm from "./components/personUpdate-form";

import * as API_USERS from "./api/person-api"
import PersonTable from "./components/person-table";



class PersonContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleInsertForm = this.toggleInsertForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this);
        this.toggleUpdateForm = this.toggleUpdateForm.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            insert:false,
            update:false,
            delete:false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchPersons();
    }

    fetchPersons() {
        return API_USERS.getPersons((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    toggleInsertForm() {
        this.setState({insert: !this.state.insert});
    }

    toggleDeleteForm() {
        this.setState({delete: !this.state.delete});
    }

    toggleUpdateForm() {
        this.setState({update: !this.state.update});
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        this.fetchPersons();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Person Management </strong>
                </CardHeader>
                <Table >
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button style={{marginRight: 1 + 'em'}} color="primary" onClick={this.toggleInsertForm}>Add Person </Button>
                            <Button style={{marginRight: 1 + 'em'}} color="primary" onClick={this.toggleDeleteForm}>Delete Person </Button>
                            <Button color="primary" onClick={this.toggleUpdateForm}>Update Person </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <PersonTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                                            errorStatus={this.state.errorStatus}
                                                            error={this.state.error}
                                                        />   }
                        </Col>
                    </Row>
                </Table>

                <Modal isOpen={this.state.insert} toggle={this.toggleInsertForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Person: </ModalHeader>
                    <ModalBody>
                        <PersonInsertForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.delete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Delete Person: </ModalHeader>
                    <ModalBody>
                        <PersonDeleteForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.update} toggle={this.toggleUpdateForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Update Person: </ModalHeader>
                    <ModalBody>
                        <PersonUpdateForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}


export default PersonContainer;
