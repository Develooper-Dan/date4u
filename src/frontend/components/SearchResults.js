import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import {ListGroup} from "react-bootstrap";
import Pagination from 'react-bootstrap/Pagination';
import Photo from "./Photo";
import placeholder from "../public/placeholder.jpg";
import Image from "next/image";

export default function SearchResults({results, switchResultPage}){

    const genderTranslation = { 0: "DIV", 1:"FEE", 2: "MA"}
    const { content, pageable, totalPages, numberOfElements, totalElements } = results;
    const elementsLowerBound = pageable.offset + 1 ;
    const elementsUpperBound = pageable.offset + numberOfElements ;

    const createPagination = () => {
        let paginationItems = [];
        for (let i = 0; i < totalPages; i++) {
            paginationItems.push(<Pagination.Item onClick={switchResultPage} data-page={i}>{i+1}</Pagination.Item>)
        }
        return paginationItems;
    }

    return (
        <>
            <div className="pagination d-flex">
                <h3>
                   Profiles {elementsLowerBound } - {elementsUpperBound } of {totalElements}
                </h3>
                <div className="ms-auto">
                    <Pagination>
                        {createPagination()}
                    </Pagination>
                </div>
            </div>

            {content.map((profile) =>
                <Card key={profile.id} style={{ width: " 20%"}} >
                    {profile.profilePhoto ?
                        <Photo filename={profile.profilePhoto.name} />
                        :
                        <Image src={placeholder} alt="placeholder"/>
                    }
                    <Card.Body>
                        <Card.Title>{profile.nickname}</Card.Title>
                        <ListGroup className="list-group-flush">
                            <ListGroup.Item>Age: {profile.age}</ListGroup.Item>
                            <ListGroup.Item>Gender: {genderTranslation[profile.gender]}</ListGroup.Item>
                            <ListGroup.Item>Hornlength: {profile.hornlength}</ListGroup.Item>
                        </ListGroup>
                        <Card.Link href={"/profile/" + profile.id}> <Button variant="primary">Go to profile</Button></Card.Link>
                    </Card.Body>
                </Card>
            )}
        </>
    )
}