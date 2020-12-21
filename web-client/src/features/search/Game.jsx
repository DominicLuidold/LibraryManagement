import { useState } from "react";
import { Button, Col, Form, Row, Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { selectServer } from "../../optionsSlice";
import { searchGame, selectGames, selectTopics } from "./searchSlice";

function Game() {
    const games = useSelector(selectGames);
    const topics = useSelector(selectTopics);
    const server = useSelector(selectServer);
    const dispatch = useDispatch();
    const [title, setTitle] = useState("");
    const [developer, setDeveloper] = useState("");
    const [platforms, setPlatforms] = useState("");
    const [topic, setTopic] = useState("");

    return (
        <Row>
            <Col xs={12} lg={4}>
                <Form>
                    <Form.Group controlId="title">
                        <Form.Label>Title</Form.Label>
                        <Form.Control placeholder="Title" onChange={e => setTitle(e.target.value)} />
                    </Form.Group>

                    <Form.Group controlId="author">
                        <Form.Label>Developer</Form.Label>
                        <Form.Control placeholder="Developer" onChange={e => setDeveloper(e.target.value)} />
                    </Form.Group>

                    <Form.Group controlId="isbn13">
                        <Form.Label>Platforms</Form.Label>
                        <Form.Control placeholder="Platforms" onChange={e => setPlatforms(e.target.value)} />
                    </Form.Group>

                    <Form.Group controlId="topic">
                        <Form.Label>Topic</Form.Label>
                        <Form.Control as="select" defaultValue="" onChange={e => setTopic(e.target.value)}>
                            <option key=""> </option>
                            {topics.map(t => {
                                return (
                                    <option key={t.id}>{t.name}</option>
                                );
                            })}
                        </Form.Control>
                    </Form.Group>

                    <Button variant="primary" onClick={() => {
                        let t = topics.find(e => e.name === topic);
                        dispatch(searchGame(title, developer, platforms, t?.id.toUpperCase(), server));
                    }
                    } >Search</Button>
                </Form>
            </Col>
            <Col xs={12} lg={8}>
                <Table hover>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Location</th>
                            <th>Availability</th>
                            <th>Developer</th>
                            <th>Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        {games.map(game => {
                            return (
                                <tr key={game.id}>
                                    <td>{game.title}</td>
                                    <td>{game.storageLocation}</td>
                                    <td>{game.availability}</td>
                                    <td>{game.developer}</td>
                                    <td><Link to={`/game/${game.id}`}>Detail</Link></td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>
            </Col>
        </Row>
    );
}

export default Game;