import { useState } from "react";
import { Button, Col, Form, Row, Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { searchDvd, selectDvds, selectTopics } from "./searchSlice";

function Dvd() {
    const dvds = useSelector(selectDvds);
    const topics = useSelector(selectTopics);
    const dispatch = useDispatch();
    const [title, setTitle] = useState("");
    const [director, setDirector] = useState("");
    const [releaseDate, setReleaseDate] = useState("");
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
                        <Form.Label>Director</Form.Label>
                        <Form.Control placeholder="Director" onChange={e => setDirector(e.target.value)} />
                    </Form.Group>

                    <Form.Group controlId="isbn13">
                        <Form.Label>Release Date</Form.Label>
                        <Form.Control type="date" placeholder="Release Date" onChange={e => setReleaseDate(e.target.value)} />
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
                        dispatch(searchDvd(title, director, releaseDate, t?.id.toUpperCase()));
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
                            <th>Director</th>
                            <th>Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dvds.map(dvd => {
                            return (
                                <tr key={dvd.id}>
                                    <td>{dvd.title}</td>
                                    <td>{dvd.storageLocation}</td>
                                    <td>{dvd.availability}</td>
                                    <td>{dvd.director}</td>
                                    <td><Link to={`/dvd/${dvd.id}`}>Detail</Link></td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>
            </Col>
        </Row>
    );
}

export default Dvd;