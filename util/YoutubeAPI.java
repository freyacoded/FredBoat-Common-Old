package fredboat.common.util;

import com.mashape.unirest.http.Unirest;
import fredboat.MusicFredBoat;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class YoutubeAPI {

    public static ArrayList<YoutubeVideo> searchForVideos(String query) {
        JSONObject data = null;
        try {
            data = Unirest.get("https://www.googleapis.com/youtube/v3/search?part=id&type=video&maxResults=5&regionCode=US&fields=items(id/videoId)")
                    .queryString("q", URLEncoder.encode(query, "UTF-8"))
                    .queryString("key", MusicFredBoat.googleServerKey)
                    .asJson()
                    .getBody()
                    .getObject();

            ArrayList<YoutubeVideo> vids = new ArrayList<>();

            data.getJSONArray("items").forEach((Object t) -> {
                JSONObject item = (JSONObject) t;
                vids.add(getVideoFromID(item.getJSONObject("id").getString("videoId")));
            });

            return vids;
        } catch (JSONException ex) {
            System.err.println(data);
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static YoutubeVideo getVideoFromID(String id) {
        JSONObject data = null;
        try {
            data = Unirest.get("https://www.googleapis.com/youtube/v3/videos?part=contentDetails,snippet&fields=items(id,snippet/title,contentDetails/duration)")
                    .queryString("id", id)
                    .queryString("key", MusicFredBoat.googleServerKey)
                    .asJson()
                    .getBody()
                    .getObject();

            YoutubeVideo vid = new YoutubeVideo();
            vid.id = data.getJSONArray("items").getJSONObject(0).getString("id");
            vid.name = data.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title");
            vid.duration = data.getJSONArray("items").getJSONObject(0).getJSONObject("contentDetails").getString("duration");

            return vid;
        } catch (JSONException ex) {
            System.err.println(data);
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
