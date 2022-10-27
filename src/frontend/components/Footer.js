import Link from "next/link";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUp } from '@fortawesome/free-solid-svg-icons';

export default function Footer(){
  return (
      <footer className="bg-dark">
          <div className="container-fluid justify-content-between">
              <Link href="#"><a><FontAwesomeIcon icon={faArrowUp} /> Up</a></Link>
              <Link href="/"><a className="nav-item nav-link">Legal notice</a></Link>
          </div>
      </footer>
  )
}