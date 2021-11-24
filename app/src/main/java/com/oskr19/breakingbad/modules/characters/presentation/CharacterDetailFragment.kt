package com.oskr19.breakingbad.modules.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.oskr19.breakingbad.R
import com.oskr19.breakingbad.core.Constants.EMTPY
import com.oskr19.breakingbad.core.presentation.BaseFragment
import com.oskr19.breakingbad.core.presentation.loadImage
import com.oskr19.breakingbad.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : BaseFragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterDetailFragmentArgs by navArgs()
    private val viewModel by activityViewModels<CharactersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(args.selectedCharacter) {
            binding.apply {
                characterDetailCharacterImg.loadImage(img)
                characterDetailNicknameTxt.text = nickname
                characterDetailOccupationTxt.text = occupation.joinToString(separator = ", ")
                characterDetailPortrayedTxt.text = portrayed
                characterDetailStatusTxt.text = status
                setFavoriteIcon()
                characterItemFavoriteImg.setOnClickListener { setFavorite() }
                setTitle(name)
                setSubTitle(EMTPY)
            }
        }
    }

    private fun setFavorite() {
        viewModel.setFavorite(args.selectedCharacter.charId, args.selectedCharacter.isFavorite)
        args.selectedCharacter.isFavorite = args.selectedCharacter.isFavorite.not()
        setFavoriteIcon()
    }

    private fun setFavoriteIcon() {
        val icon = if (args.selectedCharacter.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.characterItemFavoriteImg.background = ResourcesCompat.getDrawable(resources, icon, null)
    }
}