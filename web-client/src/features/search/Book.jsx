import { useState } from "react";
import { Button, Form, InputGroup } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { searchBook, selectBooks } from "./searchSlice";

function Book() {
    const books = useSelector(selectBooks);
    const dispatch = useDispatch();
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [isbn13, setIsbn13] = useState("");

    books.forEach(element => {
        console.log(element);
    });

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

                {/*TODO Topic */}
                <Button variant="primary" onClick={() => dispatch(searchBook(title, author, isbn13))} >Search</Button>
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