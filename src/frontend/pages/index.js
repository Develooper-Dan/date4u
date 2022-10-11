import Layout from "../components/layout";

export const getServerSideProps = async () => {
  const res = await fetch(
      `http://localhost:8080/`);
  console.log(res);
  return { props: {
    }
  }
}

export default function Home() {
  return (
      <Layout>
        <div className="jumbotron">
          <h1 className="display-4">Welcome</h1>
          <p>There's a total of ... profiles in the database</p>
        </div>
      </Layout>
  )
}

