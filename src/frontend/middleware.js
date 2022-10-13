import {NextResponse, NextRequest} from 'next/server';
import {getSession} from "next-auth/react";


export async function middleware(request = NextRequest) {
    if (request.nextUrl.pathname !== "/login" && request.nextUrl.pathname !== "/register") {
        const session = await getSession({ request });
        if (!session) return NextResponse.redirect(`${request.nextUrl.origin}/login`);
    }
    return NextResponse.next();
}
