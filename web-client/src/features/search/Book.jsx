import { Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { searchBook, selectBooks } from "./searchSlice";

function Book() {
    const books = useSelector(selectBooks);
    const dispatch = useDispatch();

    books.forEach(element => {
        console.log(element);
    });

    return (
        <>
            <Button variant="primary" onClick={() => dispatch(searchBook())} >Search</Button>

            {books.map(book => {
                return (
                    <p key={book.id}>{book.title} <Link to={`/book/${book.id}`}>Detail</Link></p>
                );
            })}
        </>
    );
}

export default Book;