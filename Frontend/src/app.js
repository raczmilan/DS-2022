import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';
import PersonContainer from './person/person-container'
import UserContainer from './user/user-container'
import DeviceContainer from './device/device-container'
import AdministratorHome from './home/administrator-home'

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';

class App extends React.Component {


    render() {

        return (
            <div className={styles.back}>
            <Router>
                <div>

                    <Switch>

                        <Route
                            exact
                            path='/'
                            render={() => <Home/>}
                        />

                        <Route path='/administrator' >
                            <div>
                                <NavigationBar />
                                <AdministratorHome/>
                            </div>
                        </Route>

                        <Route path='/person' >
                            <div>
                                <NavigationBar />
                                <PersonContainer/>
                            </div>
                        </Route>

                        <Route path='/users' >
                            <div>
                                <NavigationBar />
                                <UserContainer/>
                            </div>
                        </Route>

                        <Route path='/client'>
                            <div>
                                <NavigationBar />
                                <PersonContainer/>
                            </div>
                        </Route>

                        <Route path='/device'>
                            <div>
                                <NavigationBar />
                                <DeviceContainer/>
                            </div>
                        </Route>

                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
