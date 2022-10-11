import Layout from "../../../components/layout";


export async function getServerSideProps(context) {
    /*context.res.setHeader(
        'Cache-Control',
        'public, s-maxage=10, stale-while-revalidate=59'
    )*/
    const { id } = context.params
    const profileData = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/profiles/${id}`);
    if (!profileData) {
        return {
            notFound: true
        }
    }
    //TODO
    //redirect unauthenticated user to login page
   /* return {
        redirect: {
            destination: '/',
            permanent: false,
        },*/
    const profile = await profileData.json();
    return {
        props: {
            profile
        }
    }
}

export default function EditProfile({profile}){
    const formSubmit = async (event) => {
        event.preventDefault();
        const {id, nickname, hornlength, gender, attractedToGender, birthdate, description} = event.target;
        const formData = {
            id: id.value,
            nickname: nickname.value,
            hornlength: hornlength.value,
            gender: gender.value,
            attractedToGender: attractedToGender.value,
            birthdate: birthdate.value,
            description: description.value,
        }
        const url = process.env.NEXT_PUBLIC_API_URL + `/profiles/${id.value}/save`;
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        await fetch(url, options);
    }
    return (
        <Layout>
            <div className="jumbotron">
                <h1 className="display-4">Profile</h1>
            </div>
            <form onSubmit= {formSubmit} >
                <input type="text" id="id" hidden readOnly value={profile.id }/>
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
                    <input type="date" defaultValue={profile.birthdate} id="birthdate"
                           className="form-control" />
                </div>

                <div className="mb-3">
                    <label htmlFor="description" className="form-label">Description</label>
                    <textarea defaultValue={profile.description} id="description"
                           className="form-control" />
                </div>

                <button type="submit" className="btn btn-primary">Save</button>
            </form>
        </Layout>
    )
}
