import React from 'react';
import { Container, Navbar } from 'react-bootstrap';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Book from './features/detail/book/Book';
import Dvd from './features/detail/dvd/Dvd';
import Game from './features/detail/game/Game';
import Search from './features/search/Search';

function App() {
  return (
    <Router>
      <Navbar bg="dark" variant="dark">
        <Navbar.Text>
          <Link to="/">Search</Link>
        </Navbar.Text>
      </Navbar>

      <Switch>
        <Container>
          <Route path="/book/:id" children={<Book />} />
          <Route path="/dvd/:id" children={<Dvd />} />
          <Route path="/game/:id" children={<Game />} />

          <Route path="/">
            <Search />
          </Route>
        </Container>
      </Switch>
    </Router>
  );
}

export default App;
