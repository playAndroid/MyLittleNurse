package nurse.little.com.mylittlenurse.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import nurse.little.com.mylittlenurse.utils.DateUtils;
import nurse.little.com.mylittlenurse.utils.Utils;

public class CalendarCard extends View {

	private static final int TOTAL_COL = 7; // 7��



	private Paint mCirclePaint; // ����Բ�εĻ���
	private Paint mCircleHollowPaint; // ���ƿ���Բ�εĻ���
	private Paint mCircleDotPaint; // ���Ƶ�Ļ���
	private Paint mCircleLinePaint; // ����ֱ�ߵĻ���

	private Paint mTextPaint; // �����ı��Ļ���
	private int mViewWidth; // ��ͼ�Ŀ��
	private int mViewHeight; // ��ͼ�ĸ߶�
	private int mCellSpace; // ��Ԫ����
	private Row rows[] ; // �����飬ÿ��Ԫ�ش��һ��
	private CustomDate mShowDate; // �Զ�������ڣ���(year,month,day
	private OnCellClickListener mCellClickListener; // ��Ԫ����ص��¼�
	private int touchSlop; //
	private boolean callBackCellSpace;

	private Cell mClickCell;
	private float mDownX;
	private float mDownY;

	private int clickday; // ��������
	private int clickmonth; // �������


	private ArrayList<Integer> shcList = new ArrayList<Integer>();
	//���ڵ�����   �Լ���Ŀ�������Ŀ��    ��������� ���ܻ����4-6�е����
    private int totalLine  = 0;


	/**
	 * ��Ԫ����Ļص�ӿ�
	 *
	 *
	 *
	 */

    private Context context;
	public interface OnCellClickListener {
		void clickDate(CustomDate date); // �ص��������

		// void changeDate(CustomDate date); // �ص��ViewPager�ı������

	}

	public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CalendarCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalendarCard(Context context) {
		super(context);
		init(context);
	}

	public CalendarCard(Context context, OnCellClickListener listener,
			ArrayList<Integer> shList, CustomDate mShowDate) {
		super(context);
		this.mCellClickListener = listener;
		init(context);
		this.shcList = shList;
		this.mShowDate = mShowDate;

		// ��ݴ��ݵ����� �ж���Ҫ��ʾ��Ԫ�ص�����
		judgeDate(mShowDate);
		rows = new Row[totalLine];

		initDate();
	}

	private void judgeDate(CustomDate mShowDate2) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String dateString = mShowDate2.year + "-" + mShowDate2.month + "-1";
		try {
			c.setTime(format.parse(dateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���µ�һ���������
		int bmonth = c.get(Calendar.DAY_OF_WEEK) - 1;

		if (bmonth < 6 && bmonth > 0) {
			bmonth = 7 - bmonth;
			totalLine++;
		} else {
			bmonth = 0;
		}
		// �������һ���������
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		int amonth = c.get(Calendar.DAY_OF_WEEK);
		if (amonth > 6) {
			amonth = 0;
		}else {
			totalLine++;
		}
		// ���ֱ����Ŀ��
		totalLine = totalLine + (c.getActualMaximum(Calendar.DAY_OF_MONTH)-amonth-bmonth)/7 ;
	}

	private void init(Context context) {
		this.context = context;
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.parseColor("#0064c9")); // 6ɫԲ��

		mCircleHollowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCircleHollowPaint.setStyle(Paint.Style.STROKE);
		mCircleHollowPaint.setStrokeWidth(5);
		mCircleHollowPaint.setColor(Color.parseColor("#0064c9")); // 6ɫ����Բ��

		mCircleDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCircleDotPaint.setStyle(Paint.Style.FILL);
		mCircleDotPaint.setColor(Color.parseColor("#0064c9")); // ����ײ�6ɫС��

		mCircleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCircleLinePaint.setStyle(Paint.Style.FILL);
		mCircleLinePaint.setStrokeWidth(1);
		mCircleLinePaint.setColor(Color.parseColor("#e0e0e0")); // ����ײ�6ɫС��

		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

	}

	private void initDate() {

		fillDate();//
	}

	private void fillDate() {
		int monthDay = DateUtils.getCurrentMonthDay(); // ����
		int lastMonthDays = DateUtils.getMonthDays(mShowDate.year,
				mShowDate.month - 1); // �ϸ��µ�����
		int currentMonthDays = DateUtils.getMonthDays(mShowDate.year,
				mShowDate.month); // ��ǰ�µ�����
		int firstDayWeek = DateUtils.getWeekDayFromDate(mShowDate.year,
				mShowDate.month);
		boolean isCurrentMonth = false;
		if (DateUtils.isCurrentMonth(mShowDate)) {
			isCurrentMonth = true;
		}
		int day = 0;
		for (int j = 0; j < totalLine; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL; // ��Ԫ��λ��
				// ����µ�
				if (position >= firstDayWeek
						&& position < firstDayWeek + currentMonthDays) {
					day++;
					rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
							mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
					// ����
					if (isCurrentMonth && day == monthDay) {
						CustomDate date = CustomDate.modifiDayForObject(
								mShowDate, day);
						rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
					}

					if (isCurrentMonth && day > monthDay) { // ��������µĽ���Ҫ�󣬱�ʾ��û��
						rows[j].cells[i] = new Cell(
								CustomDate.modifiDayForObject(mShowDate, day),
								State.UNREACH_DAY, i, j);
					}

					// ��ȥһ����
				} else if (position < firstDayWeek) {
					rows[j].cells[i] = new Cell(new CustomDate(mShowDate.year,
							mShowDate.month - 1, lastMonthDays
									- (firstDayWeek - position - 1)),
							State.PAST_MONTH_DAY, i, j);
					// �¸���
				} else if (position >= firstDayWeek + currentMonthDays) {
					rows[j].cells[i] = new Cell((new CustomDate(mShowDate.year,
							mShowDate.month + 1, position - firstDayWeek
									- currentMonthDays + 1)),
							State.NEXT_MONTH_DAY, i, j);
				}
			}
		}
		// mCellClickListener.changeDate(mShowDate);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < totalLine; i++) {
			if (rows[i] != null) {
				rows[i].drawCells(canvas);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
		mViewHeight = h;
		mCellSpace = Math.min(mViewHeight / totalLine, mViewWidth / TOTAL_COL);
		Log.e("mecell", mCellSpace+"");
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
		mTextPaint.setTextSize(mCellSpace / 3);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			mDownY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float disX = event.getX() - mDownX;
			float disY = event.getY() - mDownY;
			if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
				int col = (int) (mDownX / mCellSpace);
				int row = (int) (mDownY / mCellSpace);
				measureClickCell(col, row);
			}
			break;
		default:
			break;
		}

		return true;
	}

	/**
	 * ������ĵ�Ԫ��
	 *
	 * @param col
	 * @param row
	 */
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= totalLine)
			return;
		if (mClickCell != null) {
			rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
		}
		if (rows[row] != null) {
			mClickCell = new Cell(rows[row].cells[col].date,
					rows[row].cells[col].state, rows[row].cells[col].i,
					rows[row].cells[col].j);

			CustomDate date = rows[row].cells[col].date;
			date.week = col;

			// ˢ�½���
			clickday = date.day;
			clickmonth = date.month;

			if (date.month == mShowDate.month) {
				mCellClickListener.clickDate(date);
				update();
			}
		}
	}

	/**
	 * ��Ԫ��
	 *
	 *
	 *
	 */
	class Row {
		public int j;

		Row(int j) {
			this.j = j;
		}

		public Cell[] cells = new Cell[TOTAL_COL];

		// ���Ƶ�Ԫ��
		public void drawCells(Canvas canvas) {
			// ����Ĳ��� Ҫ����ֱ�� e0e0e0

			for (int i = 0; i < totalLine; i++) {

				if (i == 0) {
					canvas.drawLine(0, i * mCellSpace, mViewWidth, i
							* mCellSpace, mCircleLinePaint);
				} else {

					canvas.drawLine(mCellSpace / 4, i * mCellSpace, mViewWidth
							- mCellSpace / 4, i * mCellSpace, mCircleLinePaint);
				}

			}

			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cells[i].drawSelf(canvas);
				}
			}
		}

	}

	/**
	 * ��Ԫ��Ԫ��
	 *
	 * @author wuwenjie
	 *
	 */
	class Cell {
		public CustomDate date;
		public State state;
		public int i;
		public int j;

		public Cell(CustomDate date, State state, int i, int j) {
			super();
			this.date = date;
			this.state = state;
			this.i = i;
			this.j = j;
		}

		public void drawSelf(Canvas canvas) {

			switch (state) {

			case TODAY: // ����

				canvas.drawCircle(
						(float) (mCellSpace * (i + 0.5)),
						(float) ((j + 0.5) * mCellSpace),
						mCellSpace / 3-7,
						mCircleHollowPaint);

				break;
			case CURRENT_MONTH_DAY: // ��ǰ������




					mTextPaint.setColor(Color.BLACK);


				break;
			case PAST_MONTH_DAY: // ��ȥһ����
				mTextPaint.setColor(Color.parseColor("#cccccc"));
				break;
			case NEXT_MONTH_DAY: // ��һ����
				mTextPaint.setColor(Color.parseColor("#cccccc"));
				break;
			case UNREACH_DAY: // ��δ������
				mTextPaint.setColor(Color.BLACK);

				break;
			default:
				mTextPaint.setColor(Color.BLACK);
				break;
			}

			// ��ѡ������
			try {
				if (mShowDate.month == date.month) {

					// ֻ�ڵ�ǰ�� ��Ч

					for (int m = 0; m < shcList.size(); m++) {
						if (date.day == shcList.get(m)) {
							mTextPaint.setColor(Color.BLACK);
							canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
									(float) ((j + 0.8) * mCellSpace+10),
									mCellSpace / 15, mCircleDotPaint);
						}
					}
//

					Calendar c = Calendar.getInstance();

					if (date.year>c.get(Calendar.YEAR)
							||date.month>c.get(Calendar.MONTH)+1
							) {
						mTextPaint.setColor(Color.BLACK);
					}else
					if (date.day < c.get(Calendar.DATE)) {
						mTextPaint.setColor(Color.parseColor("#cccccc"));


					}

					if (clickday == date.day && clickmonth == date.month) {



						mTextPaint.setColor(Color.parseColor("#fffffe"));


						canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
								(float) ((j + 0.5) * mCellSpace),
								mCellSpace / 3-7, mCirclePaint);
					}


//					if (clickday!=c.get(Calendar.DATE)) {
//						mTextPaint.setColor(Color.BLACK);
//					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			// ��������
			Calendar c = Calendar.getInstance();
			if (date.day==c.get(Calendar.DATE)&&clickday == date.day && clickmonth == date.month) {
				mTextPaint.setColor(Color.parseColor("#fffffe"));
			} else if (date.day==c.get(Calendar.DATE)&&mShowDate.month == date.month){
				mTextPaint.setColor(Color.BLACK);
			}

			String content = date.day + "";
			canvas.drawText(content,
					(float) ((i + 0.5) * mCellSpace - mTextPaint
							.measureText(content) / 2), (float) ((j + 0.7)
							* mCellSpace - mTextPaint
							.measureText(content, 0, 1) / 2), mTextPaint);
		}
	}


	enum State {
		TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
	}

	// �������һ�����һ����
	public void leftSlide() {
		if (mShowDate.month == 1) {
			mShowDate.month = 12;
			mShowDate.year -= 1;
		} else {
			mShowDate.month -= 1;
		}
		update();
	}

	// �������󻮣���һ����
	public void rightSlide() {
		if (mShowDate.month == 12) {
			mShowDate.month = 1;
			mShowDate.year += 1;
		} else {
			mShowDate.month += 1;
		}
		update();
	}

	public void update() {
		fillDate();
		invalidate();
	}

	public void initCalDate(ArrayList<Integer> intList) {
		// ��ԭ������Ļ��Ͻ��л���
		Log.e("test", "init");

	}

	public void changeState(ArrayList<Integer> intList) {
		// ��ԭ������Ļ��Ͻ��л���
		Log.e("test", "init");
		shcList = intList;
		invalidate();

	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.e("mecell------", mCellSpace+"");
		if (mCellSpace==0) {
			Log.e("test", totalLine+"---");
			setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
					totalLine*  Utils.dip2px(context, 52));
		}else {
			setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
					mCellSpace*totalLine);
		}


	}

	public void clickDate(CustomDate date){

		clickday = date.day;
		clickmonth = date.month;

		update();
	}


}
