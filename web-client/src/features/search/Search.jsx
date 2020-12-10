import { Tab, Tabs } from "react-bootstrap";
import Book from "./Book";
import Dvd from "./Dvd";
import Game from "./Game";

function Search() {
    return (
        <>
            <h1>Search</h1>
            <Tabs defaultActiveKey="book" id="uncontrolled-tab-example">
                <Tab eventKey="book" title="Book" >
                    <Book />
                </Tab>
                <Tab eventKey="dvd" title="Dvd">
                    <Dvd />
                </Tab>
                <Tab eventKey="game" title="Game">
                    <Game />
                </Tab>
            </Tabs>
        </>
    );
}

export default Search;