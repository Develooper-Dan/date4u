export async function getServerSideProps(context) {
    /*context.res.setHeader(
        'Cache-Control',
        'public, s-maxage=10, stale-while-revalidate=59'
    )*/
    const { id } = context.params
    const profileData = await fetch(
        `${process.env.API_URL}/profiles/${id}`);

    const profile = await profileData.json();
    return { props: {
            profile
        }
    }
}