import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { searchBook, selectBooks, selectTopics } from "./searchSlice";

function Book() {
    const books = useSelector(selectBooks);
    const topics = useSelector(selectTopics);
    const dispatch = useDispatch();
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [isbn13, setIsbn13] = useState("");
    const [topic, setTopic] = useState("");

    return (
        <>
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
                    dispatch(searchBook(title, author, isbn13, t?.id.toUpperCase()));
                }
                } >Search</Button>
            </Form>


            {books.map(book => {
                return (
                    <p key={book.id}>{book.title} <Link to={`/book/${book.id}`}>Detail</Link></p>
                );
            })}
        </>
    );
}

export default Book;