import { Component, OnInit , Input} from '@angular/core';
import { SongApiService } from '../../../services/song-api.service';
import { AppConfig }from '../../../config/config.constant';
@Component({
  selector: 'app-song-card',
  templateUrl: './song-card.component.html',
  styleUrls: ['./song-card.component.css']
  //providers:[SongApiService]
})
export class SongCardComponent implements OnInit {
  @Input() songs: any=[];
  public ImagePathget=AppConfig.urlImgget;
  public SongPathget=AppConfig.urlsongget;
  constructor(private songApiService: SongApiService) { }

  ngOnInit() {
  }

}
