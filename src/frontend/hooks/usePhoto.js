import useSWR from 'swr'
import fetcher from "./fetcher";

export default function usePhoto(filename, token) {

    const {
        data,
        error,
    } = useSWR( filename ? [`${process.env.NEXT_PUBLIC_API_URL}/photos/${filename}` , token] : null, fetcher, {
        revalidateIfStale: false,
    })

    return {
        base64PhotoUrl: data,
        error,
    }
}