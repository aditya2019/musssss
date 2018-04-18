import { Component, OnInit , Input} from '@angular/core';
import { SongApiService } from '../../../services/song-api.service';
import { AppConfig }from '../../../config/config.constant';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
@Component({
  selector: 'app-song-card',
  templateUrl: './song-card.component.html',
  styleUrls: ['./song-card.component.css'],
  //providers:[SongApiService]
})
export class SongCardComponent implements OnInit {
  @Input() songs: any=[];
  @Input() flag: string;
  public ImagePathget=AppConfig.urlImgget;
  public SongPathget=AppConfig.urlsongget;
  public currentsong : any={};
  constructor(private songApiService: SongApiService,private http:Http) { }

  ngOnInit() {
  }
   setSong(song){
     this.currentsong=song;
   }

   addToMymusic(song)
   {
     this.songApiService.updateMymusic(song).subscribe(data=>{
       console.log(data);
     },(error:any)=>{
     })
   }

}
