import withPageContext, {PageContext} from "../../components/hoc/withPageContext"
import ContainerBox from "../../components/ContainerBox";
import {Songs} from "../../components/songs/Songs";
import {PlaylistDetails, PlaylistTitle} from "../../components/playlists/PlaylistInfos";
import {useContext, useState} from "react";
import {YoutubeSearch} from "../../components/songs/YoutubeSearch";
import {YoutubeVideo} from "../../components/songs/YoutubeVideo";
import {SongProps} from "../../components/songs/Song";
import useClient from "../../components/hooks/useClient";
import StatusCode from "../../shared/StatusCode";

export enum StepType {
  MAIN,
  PLAY,
  ADD
}

const SongsPage = () => {
  const client = useClient()
  const context = useContext(PageContext)
  const [currentPlaylist] = useState(context.currentPlaylist)
  const [songs, setSongs] = useState(context.songs)
  const [playedSong, setPlayedSong] = useState<SongProps>()
  const [step, setStep] = useState(StepType.MAIN)

  const refreshSongs = async () => {
    const res = await client.get(`/refresh-songs?p=${currentPlaylist.playlistId}`)
    switch (res.statusCode) {
      case StatusCode.OK:
        setSongs(res.songs)
        break
      default:
        break
    }
  }

  const render = () => {
    switch (step) {
      case StepType.MAIN:
        return (
          <ContainerBox
            left={
              <Songs
                playlist={currentPlaylist}
                songs={songs}
                playedSongId={playedSong?.songId}
                setPlayedSong={setPlayedSong}
                setStep={setStep}
                refreshSongs={refreshSongs}
                setSongs={setSongs}
              />
            }
            right={
              <>
                <PlaylistTitle playlist={currentPlaylist} setStep={setStep} />
                <PlaylistDetails playlist={currentPlaylist} bookmarkCount={context.bookmarkCount}/>
              </>
            }
          />
        )
      case StepType.ADD:
        return (
          <ContainerBox
            left={
              <Songs
                playlist={currentPlaylist}
                songs={songs}
                playedSongId={playedSong?.songId}
                setPlayedSong={setPlayedSong}
                setStep={setStep}
                refreshSongs={refreshSongs}
                setSongs={setSongs}
              />}
            right={
              <>
                <PlaylistTitle playlist={currentPlaylist} setStep={setStep} />
                <YoutubeSearch playlist={currentPlaylist} />
              </>
            }
          />
        )
      case StepType.PLAY:
        return (
          <ContainerBox
            left={
              <Songs
                playlist={currentPlaylist}
                songs={songs}
                playedSongId={playedSong?.songId}
                setPlayedSong={setPlayedSong}
                setStep={setStep}
                refreshSongs={refreshSongs}
                setSongs={setSongs}
              />}
            right={
              <>
                <PlaylistTitle playlist={currentPlaylist} setStep={setStep} />
                {playedSong &&
                  <YoutubeVideo
                    playlist={currentPlaylist}
                    songs={songs}
                    playedSong={playedSong}
                    refreshSongs={refreshSongs}
                    setPlayedSong={setPlayedSong}
                  />
                }
              </>
            }
          />
        )
      default:
        return (<></>)
    }
  }


  return render()
}

export default withPageContext()(SongsPage)