import useSWR from 'swr'
import fetcher from "./fetcher";

export default function useUser(token) {
    const {
        data,
        error,
    } = useSWR([`${process.env.NEXT_PUBLIC_API_URL}/unicorn`, token], fetcher, {
        revalidateIfStale: false,
        shouldRetryOnError: false
    })

    return {
        user: data,
        isLoading: !error && !data,
        error,
    }
}