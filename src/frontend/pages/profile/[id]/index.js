import Layout from "../../../components/layout";

export async function getServerSideProps(context) {
    /*context.res.setHeader(
        'Cache-Control',
        'public, s-maxage=10, stale-while-revalidate=59'
    )*/
    const { id } = context.params
    const profileData = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/profiles/${id}`);

    const profile = await profileData.json();
    return { props: {
            profile
        }
    }
}

export default function Profile({profile}){
    return (
        <Layout>
            <div className="jumbotron">
                <h1 className="display-4">Profile</h1>
            </div>
            <form action="/save" method="post">
                <input type="text" id="id" hidden readOnly={profile.id }/>
                    <div className="mb-3">
                        <label htmlFor="nickname" className="form-label">Nickname</label>
                        <input type="text" defaultValue={profile.nickname} id="nickname"
                               className="form-control" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="hornlength" className="form-label">Hornlength</label>
                        <input type="text" defaultValue={profile.hornlength} id="hornlength"
                               className="form-control" />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="gender" className="form-label">Gender</label>
                        <select id="gender">
                            <option selected={ profile.gender === 0 } value="0" >DIV</option>
                            <option selected={ profile.gender === 1 } value="1" >FEE</option>
                            <option selected={ profile.gender === 2 } value="2" >MA</option>
                        </select>
                    </div>

                    <div className="mb-3">
                        <label htmlFor="attractedToGender" className="form-label">Attracted to gender</label>
                        <input type="text" defaultValue={profile.attractedToGender} id="attractedToGender"
                               className="form-control" />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="birthdate" className="form-label">Birthdate</label>
                        <input type="date" defaultValue={profile.birthdate} id="birthdateInput"
                               className="form-control" />
                    </div>

                    <button type="submit" className="btn btn-primary">Save</button>
            </form>
        </Layout>
    )
}
