import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { loadBookDetail, selectBookDetail } from "../detailSlice";

function Book() {
    let { id } = useParams();
    const dispatch = useDispatch();
    dispatch(loadBookDetail(id));
    const bookDetail = useSelector(selectBookDetail);
    console.log(bookDetail);

    return (
        <h1>Detail of Book {id}</h1>
    );
}

export default Book;