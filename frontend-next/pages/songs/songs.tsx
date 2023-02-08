import withPageContext, {PageContext} from "../../components/hoc/withPageContext"
import ContainerBox from "../../components/ContainerBox";
import {Songs} from "../../components/songs/Songs";
import {PlaylistDescription, PlaylistTitle} from "../../components/playlists/PlaylistDetail";
import {useContext, useState} from "react";
import {YoutubeSearch} from "../../components/songs/YoutubeSearch";

export enum StepType {
  MAIN,
  PLAY,
  ADD
}

const SongsPage = () => {
  const context = useContext(PageContext)
  const [currentPlaylist] = useState(context.currentPlaylist)
  const [songs] = useState(context.songs)
  const [step, setStep] = useState(StepType.MAIN)

  const render = () => {
    switch (step) {
      case StepType.MAIN:
        return (
          <ContainerBox
            left={<Songs playlist={currentPlaylist} songs={songs} setVideoId={setVideoId}/>}
            right={
              <>
                <PlaylistTitle playlist={currentPlaylist} setStep={setStep} />
                <PlaylistDescription playlist={currentPlaylist} />
              </>
            }
          />
        )
      case StepType.ADD:
        return (
          <ContainerBox
            left={<Songs playlist={currentPlaylist} songs={songs} setVideoId={setVideoId} />}
            right={
              <>
                <PlaylistTitle playlist={currentPlaylist} setStep={setStep} />
                <YoutubeSearch playlist={currentPlaylist} />
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