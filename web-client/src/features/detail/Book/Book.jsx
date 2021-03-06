import { Button, Col, Row, Spinner, Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link, useParams } from "react-router-dom";
import { selectServer } from "../../../optionsSlice";
import { selectTopics } from "../../search/searchSlice";
import { selectBookDetail } from "../detailSlice";

function Book() {
    let { id } = useParams();
    const bookDetail = useSelector(selectBookDetail);
    const topics = useSelector(selectTopics);
    const server = useSelector(selectServer);
    const dispatch = useDispatch();

    // if (bookDetail?.details.id !== id) {
    //     dispatch(loadBookDetail(id, server));
    // }

    // if (topics.length === 0) {
    //     dispatch(loadTopics(server));
    // }

    return (
        <>
            <Row>
                <Col>
                    {!bookDetail &&
                        <Spinner animation="border" />
                    }
                    <h1>{bookDetail?.details.title}</h1>
                </Col>
            </Row>
            <Row>
                <Col xs={12} lg={4}>
                    <Table borderless={true}>
                        <tbody>
                            <tr>
                                <td><b>Author</b></td>
                                <td>{bookDetail?.details.author}</td>
                            </tr>
                            <tr>
                                <td><b>ISBN 10</b></td>
                                <td>{bookDetail?.details.isbn10}</td>
                            </tr>
                            <tr>
                                <td><b>ISBN 13</b></td>
                                <td>{bookDetail?.details.isbn13}</td>
                            </tr>
                            <tr>
                                <td><b>Publisher</b></td>
                                <td>{bookDetail?.details.publisher}</td>
                            </tr>
                            <tr>
                                <td><b>Language</b></td>
                                <td>{bookDetail?.details.languageKey}</td>
                            </tr>
                            <tr>
                                <td><b>Release</b></td>
                                <td>{bookDetail?.details.releaseDate[0]}</td>
                            </tr>
                            <tr>
                                <td><b>Topic</b></td>
                                <td>{topics.find(t => t.id === bookDetail?.details.topic)?.name}</td>
                            </tr>
                        </tbody>
                    </Table>

                    <Link to={`/`}><Button>Back</Button></Link>
                </Col>
                <Col xs={12} lg={8}>
                    <Table>
                        <thead>
                            <tr>
                                <th>Copy ID</th>
                                <th>Lend Till</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                bookDetail?.copies?.map(copy => {
                                    return (
                                        <tr key={copy.id} style={copy.available ? { background: "#68c17c" } : { background: "#e6717c" }}>
                                            <td>{copy.id}</td>
                                            <td>{copy.available || !copy.lendTill ? "-" : `${copy.lendTill[0]}-${copy.lendTill[1]}-${copy.lendTill[2]}`}</td>
                                        </tr>
                                    )
                                })
                            }

                        </tbody>
                    </Table>

                </Col>
            </Row>
        </>
    );
}

export default Book;