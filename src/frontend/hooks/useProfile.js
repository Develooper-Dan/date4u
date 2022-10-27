import useSWR from 'swr'
import fetcher from "./fetcher";



export default function useProfile(id, token) {
    const {
        data,
        error,
        mutate
    } = useSWR( id ? [`${process.env.NEXT_PUBLIC_API_URL}/profiles/${id}` , token] : null, fetcher, {
        revalidateIfStale: false,
        shouldRetryOnError: false
    })

    return {
        profile: data,
        error,
        mutate
    }
}