import { Button, Col, Row, Spinner, Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link, useParams } from "react-router-dom";
import { selectServer } from "../../../optionsSlice";
import { selectTopics } from "../../search/searchSlice";
import { selectDvdDetail } from "../detailSlice";

function Dvd() {
    let { id } = useParams();
    const dvdDetail = useSelector(selectDvdDetail);
    const topics = useSelector(selectTopics);
    const server = useSelector(selectServer);
    const dispatch = useDispatch();

    // if (dvdDetail?.details.id !== id) {
    //     dispatch(loadDvdDetail(id, server));
    // }

    // if (topics.length === 0) {
    //     dispatch(loadTopics(server));
    // }

    return (
        <>
            <Row>
                <Col>
                    {!dvdDetail &&
                        <Spinner animation="border" />
                    }
                    <h1>{dvdDetail?.details.title}</h1>
                </Col>
            </Row>
            <Row>
                <Col xs={12} lg={4}>
                    <Table borderless={true}>
                        <tbody>
                            <tr>
                                <td><b>Director</b></td>
                                <td>{dvdDetail?.details.director}</td>
                            </tr>
                            <tr>
                                <td><b>Studio</b></td>
                                <td>{dvdDetail?.details.studio}</td>
                            </tr>
                            <tr>
                                <td><b>Duration</b></td>
                                <td>{dvdDetail?.details.durationMinutes} min</td>
                            </tr>
                            <tr>
                                <td><b>Actors</b></td>
                                <td>{dvdDetail?.details.actors}</td>
                            </tr>
                            <tr>
                                <td><b>Age Restriction</b></td>
                                <td>{dvdDetail?.details.ageRestriction}</td>
                            </tr>
                            <tr>
                                <td><b>Release</b></td>
                                <td>{dvdDetail?.details.releaseDate[0]}</td>
                            </tr>
                            <tr>
                                <td><b>Topic</b></td>
                                <td>{topics.find(t => t.id === dvdDetail?.details.topic)?.name}</td>
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
                                dvdDetail?.copies?.map(copy => {
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

export default Dvd;