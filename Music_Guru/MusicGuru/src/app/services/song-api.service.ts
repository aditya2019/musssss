import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { AppConfig }from '../config/config.constant';
@Injectable()
export class SongApiService {
  private headers = new Headers({ 'Content-Type': 'application/json' });
  constructor(private http:Http) { }

getAllSong()
{
  return this.http.get(AppConfig.getAll)
  .map(data => data.json(),
    (error: any)=>this.handleError(error));
}

 updateDB(getData){
  	console.log(getData);
  	return this.http.post(AppConfig.uploadSongUrl,getData, {headers: this.headers})
  	.map(data => data.json(),
  		(error: any)=>this.handleError(error));
  }
  // Handle errors
  private handleError(error: Response){
    return Observable.throw(error);
  }
  updateMymusic(putsong)
  {
    return this.http.post(AppConfig.mymusicdb,putsong, {headers: this.headers})
  	.map(data => data.json(),
  		(error: any)=>this.handleError(error));
  }
  //mymusic get all song
  getAllMySong()
  {
    return this.http.get(AppConfig.getAllMymusic)
    .map(data => data.json(),
      (error: any)=>this.handleError(error));
  }

}
