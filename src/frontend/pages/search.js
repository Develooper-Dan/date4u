import Layout from "../components/Layout";
import Cookies from "js-cookie";
import {useForm} from "react-hook-form";
import axios from "axios";
import {useEffect, useState} from "react";
import SearchResults from "../components/SearchResults";
import {useRouter} from "next/router";



export default function Search(){
    const token = Cookies.get("token");
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [searchResults, setSearchResults] = useState(null);
    const [searchRequest, setSearchRequest] = useState(null);
    const hornLengthOptions = [...Array(41).keys()]; // 0 - 40
    const ageOptions = [...Array(82).keys()].map((baseAge) => baseAge + 18 ); // 18 - 99

    const search = (formData, ...addtionalArgs) => {

        setSearchRequest(formData);
        let axiosOptions = {
            method: "post",
            url: `${process.env.NEXT_PUBLIC_API_URL}/search`,
            data: formData,
            headers: {"Content-Type": "application/json", "Authorization": "Bearer " + token}
        }

        const queryParams = addtionalArgs.find(obj => obj.query);
        if(queryParams){
            axiosOptions.params = queryParams.query;
        }

        axios(axiosOptions).then(res => {
            if (res.status < 400 ) {
                setSearchResults(res.data);
            }
        }).catch(err => {
        if (err) {
            console.log(err);
        }
        })
    }


    const switchResultPage = (event) => {
        const queryParams = {query:{page: event.target.dataset.page}};
        search(searchRequest, queryParams);
    }

    return (
            <Layout>
                <div className="jumbotron w-100 mb-3">
                    <h1 className="display-4">Search</h1>
                </div>
                <section className = "row mb-3">
                    <form onSubmit= {handleSubmit(search)} id="searchForm">
                        <div className="mb-3">
                            <label htmlFor="nickname" className="form-label">Nickname</label>
                            <input type="text" className="form-control"
                                   placeholder="HornyDevil"
                                   {...register("nickname", {
                                       maxLength: {value: 32, message: "only 32 characters allowed"}
                                   })} />
                            {errors.nickname && <p role="alert">{errors.nickname?.message}</p>}

                        </div>
                        <fieldset form = "searchForm mb-3">
                            <legend>Age between</legend>
                                <label htmlFor="minAge" className="form-label">min</label>
                                <select defaultValue={ageOptions[0]}  {...register("minAge", {valueAsNumber: true})} >
                                    {ageOptions.map((age) => {
                                      return <option key={"min"+ age}value={age}>{age} years</option>
                                    })}
                                </select>
                                <label htmlFor="maxAge" className="form-label">max</label>
                                <select defaultValue={ageOptions[ageOptions.length -1]} {...register("maxAge", {valueAsNumber: true})} >
                                    {ageOptions.map((age) => {
                                      return <option key={"max"+ age}value={age}>{age} years</option>
                                    })}
                                </select>
                        </fieldset>
                        <fieldset form = "searchForm mb-3">
                            <legend>Hornlength between</legend>
                                <label htmlFor="minHornlength" className="form-label">min</label>
                                <select defaultValue={hornLengthOptions[0]} {...register("minHornlength", {valueAsNumber: true})} >
                                    {hornLengthOptions.map((length) => {
                                      return <option key={"min"+ length}value={length}>{length} cm</option>
                                    })}
                                </select>
                                <label htmlFor="maxHornlength" className="form-label">max</label>
                                <select defaultValue={hornLengthOptions[hornLengthOptions.length -1]} {...register("maxHornlength", {valueAsNumber: true})} >
                                    {hornLengthOptions.map((length, index) => {
                                      return <option key={"max"+ length}value={length}>{length} cm</option>
                                    })}
                                </select>
                        </fieldset>

                        <div className="mb-3">
                            <label htmlFor="gender" className="form-label">Gender</label>
                            <select className="form-control" defaultValue="ALL" {...register("gender", { valueAsNumber: true})} >
                                <option value="0">DIV</option>
                                <option value="1">FEE</option>
                                <option value="2">MA</option>
                                <option value={null}>ALL</option>
                            </select>
                        </div>

                        <div className="mb-3">
                            <label htmlFor="attractedToGender" className="form-label">Attracted To</label>
                            <select className="form-control" defaultValue="N/A" {...register("attractedToGender", {valueAsNumber: true})} >
                                <option value="0" >DIV</option>
                                <option value="1" >FEE</option>
                                <option value="2" >MA</option>
                                <option value={null} >N/A</option>
                            </select>
                        </div>

                        <button type="submit" className="btn btn-primary">Search</button>
                    </form>
                </section>
                <section className="row results">

                    {searchResults ? <SearchResults results={searchResults} switchResultPage={switchResultPage} /> : <p>No results found.</p>}
                </section>
            </Layout>
    )
}