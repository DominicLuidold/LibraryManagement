import { useParams } from "react-router-dom";

function Book() {
    let { id } = useParams();

    return (
        <h1>Detail of Book {id}</h1>
    );
}

export default Book;