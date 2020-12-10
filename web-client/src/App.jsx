import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Book from './features/detail/Book/Book';
import Dvd from './features/detail/Dvd/Dvd';
import Game from './features/detail/Game/Game';
import Search from './features/search/Search';

function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Search</Link>
            </li>
          </ul>
        </nav>

        <Switch>
          <Route path="/book/:id" children={<Book />} />
          <Route path="/dvd/:id" children={<Dvd />} />
          <Route path="/game/:id" children={<Game />} />

          <Route path="/">
            <Search />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
