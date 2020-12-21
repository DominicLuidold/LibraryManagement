import { useState } from "react";
import { Button, Col, Form, Row, Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { selectServer } from "../../optionsSlice";
import { searchBook, selectBooks, selectTopics } from "./searchSlice";

function Book() {
    const books = useSelector(selectBooks);
    const topics = useSelector(selectTopics);
    const server = useSelector(selectServer);
    const dispatch = useDispatch();
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [isbn13, setIsbn13] = useState("");
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
                        <Form.Label>Author</Form.Label>
                        <Form.Control placeholder="Author" onChange={e => setAuthor(e.target.value)} />
                    </Form.Group>

                    <Form.Group controlId="isbn13">
                        <Form.Label>ISBN 13</Form.Label>
                        <Form.Control placeholder="ISBN 13" onChange={e => setIsbn13(e.target.value)} />
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
                        dispatch(searchBook(title, author, isbn13, t?.id.toUpperCase(), server));
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
                            <th>Author</th>
                            <th>Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        {books.map(book => {
                            return (
                                <tr key={book.id}>
                                    <td>{book.title}</td>
                                    <td>{book.storageLocation}</td>
                                    <td>{book.availability}</td>
                                    <td>{book.author}</td>
                                    <td><Link to={`/book/${book.id}`}>Detail</Link></td>
                                </tr>
                            );
                        })}
                    </tbody>
                </Table>
            </Col>
        </Row>
    );
}

export default Book;