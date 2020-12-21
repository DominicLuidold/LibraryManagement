import React from 'react';
import { Container, Form, Nav, Navbar } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import {
  BrowserRouter as Router,


  Link, Route, Switch
} from "react-router-dom";
import Book from './features/detail/Book/Book';
import Dvd from './features/detail/Dvd/Dvd';
import Game from './features/detail/Game/Game';
import Search from './features/search/Search';
import { setServer } from './optionsSlice';

function App() {
  const dispatch = useDispatch();

  return (
    <Router>
      <Navbar bg="dark" variant="dark">
        <Nav className="mr-auto">
          <Navbar.Text>
            <Link to="/">Search</Link>
          </Navbar.Text>
        </Nav>
        <Nav >
          <Form inline>
            <Form.Group controlId="topic">
              <Form.Control as="select" defaultValue="azure" onChange={e => dispatch(setServer(e.target.value))}>
                <option key="azure">http://vsts-team007.westeurope.cloudapp.azure.com</option>
                <option key="localhost">http://localhost</option>
              </Form.Control>
            </Form.Group>
          </Form>
        </Nav>
      </Navbar>

      <Switch>
        <Container>
          <Route exact path="/">
            <Search />
          </Route>

          <Route path="/book/:id" children={<Book />} />
          <Route path="/dvd/:id" children={<Dvd />} />
          <Route path="/game/:id" children={<Game />} />
        </Container>
      </Switch>
    </Router >
  );
}

export default App;
