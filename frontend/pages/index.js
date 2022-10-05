
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
      <main>
        <div className="container">
          <nav className="navbar navbar-expand-lg bg-dark navbar-dark">
            <div className="container-fluid">
              <div className="navbar-nav">
                <a className="navbar-brand" href="/">Date4u</a>
                <a className="nav-item nav-link" href="/">Home</a>
                <a className="nav-link nav-item" href="/profile">Profile</a>
                <a className="nav-link nav-item" href="/search">Search</a>
              </div>
            </div>
          </nav>
          <div className="jumbotron">
            <h1 className="display-4">Welcome</h1>
            <p>There's a total of ... profiles in the database</p>
          </div>
         </div>
      </main>
  )
}
