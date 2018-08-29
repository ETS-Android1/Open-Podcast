package com.the_canuck.openpodcast.fragments.library;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.the_canuck.openpodcast.Podcast;
import com.the_canuck.openpodcast.R;
import com.the_canuck.openpodcast.sqlite.MySQLiteHelper;

import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LibraryFragment extends Fragment implements LibraryContract.LibraryView {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 3;

    private OnListFragmentInteractionListener mListener;
//    private MySQLiteHelper sqLiteHelper;
    private List<Podcast> podcastList;
    private LibraryContract.LibraryPresenter mLibPresenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LibraryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LibraryFragment newInstance(int columnCount) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLibPresenter = new LibraryPresenter(this, getContext());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library_list, container, false);

        mLibPresenter.updateSubscribedPodcasts();

//        sqLiteHelper = new MySQLiteHelper(getContext());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            // TODO: Set column count as a filter option and change here
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

            recyclerView.setAdapter(new MyLibraryRecyclerViewAdapter
                    (podcastList, mListener, recyclerView));
        }
        return view;
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        // TODO: Put a loading view in later
    }

    @Override
    public void showSubscribedPodcasts(List<Podcast> podcasts) {
        podcastList = podcasts;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteractionLibrary(Podcast item);
    }
}
