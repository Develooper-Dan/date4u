import Layout from "../components/Layout";

export const getServerSideProps = async () => {
    const res = await fetch(
        `http://localhost:8080/`);
    console.log(res);
    return { props: {
        }
    }
}

export default function Search(){
    return (
        <Layout>
            <div className="jumbotron">
                <h1 className="display-4">Search</h1>
            </div>
            <table className="table">
                <thead>
                <tr>
                    <th scope="col">Nickname</th>
                    <th scope="col">Birthdate</th>
                    <th scope="col">Hornlength</th>
                    <th scope="col">Gender</th>
                </tr>
                </thead>
                <tbody>
                <tr data-th-each="profile,iterationStatus : ${profiles}">
                    <td data-th-text="${profile.nickname}">Name</td>
                    <td data-th-text="${profile.birthdate}">Birthdate</td>
                    <td data-th-text="${profile.hornlength}">Hornlength</td>
                    <td data-th-text="${profile.gender}">Gender</td>
                </tr>
                </tbody>
            </table>
        </Layout>
    )
}